from bottle import route, run, get, post, request, response
from pprint import pprint
import json
import requests
import gensim
import traceback
from bs4 import BeautifulSoup

#import sparql_template_generator
verbalizing_url = "http://km.aifb.kit.edu/projects/spartiqulator/v5/verbalize.pl?"
model = gensim.models.KeyedVectors.load_word2vec_format('/home/gaurav/Downloads/models/word2vec/GoogleNews-vectors-negative300.bin', binary=True)  

#generating sparql templates
@post('/templates') 
def templates():
	data = request.json
	print data
	# raw_input()
	entities = [x['ent'] for x in data['entities']]
	predicates = [x['pred'] for x in data['predicates']]
	sparqlgen = sparql_template_generator.SPARQL_Template_Generator(entities,predicates)
	print sparqlgen
	#making a dictionary of the questions and answers
	info = []

	for tup in sparqlgen.sparqls_answers:
		if tup[1]:
			info += [{'sparql':tup[0],'answer':tup[1]}]
	response.content_type = 'application/json'
	return json.dumps(info)


#for finding similarity between words using word2vec
@post('/similarity')
def hello():
	word1=request.forms.get('word1')
	word2=request.forms.get('word2')
	# word1 = request.forms.get("word1")
	# word2 = request.forms.get("word2")
	print "First word: ", word1, len(word1)
	print "Second word: ", word2, len(word2)

	if len(word1) < 1 or len(word2) < 1:
		return str(-2)

	if ' ' in word1 or ' '  in word2:
		try:
			similarity_value = model.n_similarity(word1.split(),word2.split())
		except Exception:
			print Exception
			similarity_value = str(-2)
	else:
		try:
			similarity_value = model.similarity(word1, word2)
		except Exception:
			# print traceback.print_exc
			print Exception
			similarity_value = str(-2)
	print "hello world"
	print similarity_value
	return str(similarity_value)


#for verbalizing queries
@post('/verbalize')
def verbalize():
	# query = '''PREFIX dbo: <http://dbpedia.org/ontology/>
	# PREFIX res: <http://dbpedia.org/resource/>
	# PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>
	# PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>
	# SELECT DISTINCT ?uri ?string 
	# WHERE {
	# 	?uri rdf:type dbo:Film .
	#         ?uri dbo:starring res:Julia_Roberts .
	#         ?uri dbo:starring res:Richard_Gere.
	# 	OPTIONAL {?uri rdfs:label ?string . FILTER (lang(?string) = 'en') }
	# }'''
	query = request.forms.get('query')

	payload = {'sparql':query}
	url = verbalizing_url
	r = requests.get(url,params=payload)

	html_content = r.content
	# print r.content
	soup = BeautifulSoup(html_content)
	question = ""

	try:
		input_tag = soup.find_all("input")
		for x in input_tag:
			counter = 0
			try:
				if x.attrs["name"] == "verbalization":
					print x.attrs["value"]
					question = x.attrs["value"]
					counter = 1
			except:
				continue
			if counter == 1:
				break
	except:
		print "no input tag found ! HTML content might have been changed"
		return str(-2)
	if question:
		print "verbalized"
		return str(question)
	else:
		print "not found"
		return str(-2)

run(host='0.0.0.0', port=8080, debug=True)



