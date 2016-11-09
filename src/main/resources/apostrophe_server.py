#This code convert apostrophe problem to "of"
#for example
# Who was John F. Kennedy's vice president? -->Who was  vice president of John F. Kennedy? 
# Does Abraham Lincoln's death place have a website? --> Does death place of Abraham Lincoln have a website?
# What was Brazil's lowest rank in the FIFA World Ranking?
# Which of Tim Burton's films had the highest budget?
# How heavy is Jupiter's lightest moon?		--> How heavy is lightest moon of Jupiter's ?
# When did Dracula's creator die?

# necessary imports for the server 
from bottle import route, run, get, post, request, response
from pprint import pprint
import json
import requests


#assuming ony one apostrophe
from copy import copy
import spacy
nlp = spacy.load('en')

#this fucntion gives the first apostrophy of the sentence. So this function needs to be called till all the apostrophe of the 
#sentence has been resolved. This is done by resolve_apostrophes (mind the "s"). 

def resolve_apostrophe(question):
	#input is a passed through nlp
	apostrophe = '''POS'''
	position_of_apostrophe = None
	valid_tags = ["NN","NNS","ADJ","JJS","JJ"]	#add more fine grained pos tags in the list
	counter = 0
	#list of entities 
	for tokens in question:
		if apostrophe in str(tokens.tag_):
			position_of_apostrophe = counter
			position_of_previous_word = counter - 1
		counter = counter + 1
	if position_of_apostrophe:
		#print question[position_of_apostrophe+1].head
		start_index = 0
		end_index = 0
		state = copy(position_of_apostrophe)
		merged_text_after_apostrophe = ""
		while str(question[state+1].tag_) in valid_tags:
			merged_text_after_apostrophe= merged_text_after_apostrophe + " " + str(question[state+1])
			end_index = int(question[state+1].idx) + len(str(question[state+1]))
			state = state + 1
		merged_text_before_apostrophe = str(question[position_of_previous_word])
		start_index = int(question[position_of_previous_word].idx)
		while str(question[position_of_previous_word-1].dep_) == "compound":
			merged_text_before_apostrophe = str(question[position_of_previous_word-1]) + " " + merged_text_before_apostrophe
			start_index = int(question[position_of_previous_word-1].idx)
			position_of_previous_word = position_of_previous_word - 1	
		paraphrased_text = merged_text_after_apostrophe.strip(), "of", merged_text_before_apostrophe.strip()
		paraphrased_text = " ".join(paraphrased_text)
		question_string = str(question)
		temp = str(question_string.replace(str(question)[start_index:end_index],paraphrased_text))
		print temp
		return temp
	else:
		return unicode(str(question))	

#expects a unicode string
def resolve_apostrophes(question):
	question = unicode(question)
	apostrophe = '''POS'''
	flag = True
	return_question_state = question
	while flag == True:
		previous_question_state = return_question_state
		return_question_state = resolve_apostrophe(nlp(question))
		if return_question_state == previous_question_state:
			flag = False
		elif ''''s''' not in return_question_state:
			flag = False
		else:
			return_question_state = resolve_apostrophe(nlp(question))
	return unicode(return_question_state)

@post('/apostrophe_resolver')
def resolve():
	question = request.forms.get('question')
	return str(resolve_apostrophes(question))


run(host='localhost', port=9000, debug=True)
