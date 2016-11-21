#make connection, db, collection for en-nl
from pymongo import MongoClient

client = MongoClient()
client.drop_database('TeamMartini')
collection = client.TeamMartini.Entries

#store words in collection
text_file = open("./data/output.txt","r")
count = 0
word1 = ""
word2 = ""
for line in text_file.readlines():
	count += 1
	if (count % 2) == 1:
		word1 = line[:-1]
	else:
		word2 = line[:-1]
		entry = {"word": word2,
			  "translation": word1,
			  "languages": ["English", "Dutch"],
			  "_class": "model.Entry"}
		collection.insert(entry)