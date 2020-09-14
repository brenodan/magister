## source https://stats.stackexchange.com/questions/123060/clustering-a-long-list-of-strings-words-into-similarity-groups
import resource
import pandas as pd
import numpy as np
import sklearn.cluster

from sklearn.cluster import MiniBatchKMeans
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.decomposition import PCA
from sklearn.datasets import load_files

import matplotlib.pyplot as plt
import distance
import string
import re

random_state = 0
DATA_DIR = "/Users/bdantasc/eclipse-workspace/credential.scan/output/strings_for_clustering1.txt"

#def isascii(s):
	#try:
    #	len(s) == len(s.encode())
    
 #   except UnicodeEncodeError:
    
  #  print("not unicode")
	#return val

#words = "YOUR WORDS HERE".split(" ") #Replace this line

my_array = []
with open(DATA_DIR) as my_file:
	i = 1
	j = 1
	for line in my_file:
		#line.replace("\"","")
		#line.replace("\n","")
		re.sub(r"\s+", "", line)
		
		val = True
		
		try:
			line.decode('ascii')
		except UnicodeDecodeError:
			val = False
		else:
			val = True
			
		#alpha = line.isalpha()
		#number = line.isnumeric()
		#res = re.search('[a-zA-Z]+',line)
		#alpha = isascii(line)  
		
		if val:
			my_array.append(line)
			print("line #" + str(i) + " : "+ line)
			j = j + 1
		
		i = i + 1
	
	print("total number of strings " + str(i) + " : number of candidates " + str(j))
print("checkpoint 1")

#words = np.asarray(list(my_array)) #converting from set to array
words = my_array

print("checkpoint 2")

lev_similarity = -1*np.array([[distance.levenshtein(w1,w2) for w1 in words] for w2 in words])

print("checkpoint 3")
affprop = sklearn.cluster.AffinityPropagation(affinity="precomputed", damping=0.5)
affprop.fit(lev_similarity)

print("checkpoint 4")
with open("/Users/bdantasc/eclipse-workspace/credential.scan/output/cluster_results1.txt", "w") as f:
	for cluster_id in np.unique(affprop.labels_):
		exemplar = words[affprop.cluster_centers_indices_[cluster_id]]
		cluster = np.unique(words[np.nonzero(affprop.labels_==cluster_id)])
		cluster_str = ", ".join(cluster)
		print(" - *%s:* %s" % (exemplar, cluster_str))
		f.write("\n - *%s:* %s" % (exemplar, cluster_str))
