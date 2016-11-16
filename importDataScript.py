#make connection, db, collection for en-nl
from pymongo import MongoClient

client = MongoClient()
client.drop_database('TeamMartini')
collection = client.TeamMartini.entries

#store words in collection
text_file = open("./data/output.txt","r")
count = 0
word1 = ""
word2 = ""
for line in text_file.readlines():
	count += 1
	if (count % 2) == 1:
		word1 = line
	else:
		word2 = line
		entry = {"word": word2,
			  "translation": word1,
			  "languages" : "EnNl"}
		collection.insert(entry)