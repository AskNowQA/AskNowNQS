/**
 * 
 */
/**
 * @author mohnish
 *This package is for machine learning module
 *Steps:
 *1. Clustering of SPARQL. Lets say S1 S2 S3 S4 Sx
 *2. Mapping of NQS to clusters in step1. Thus, NQS forms clusters. Say N1->S1 N2->S2 N3->S3 N4->S4 Nx->Sx
 *3. Classify new NQS (from test Query) to one of the clusters Nx
 *4. cluster Nx will have sparql pattern only from Sx.
 */
package org.aksw.asknow.ml;