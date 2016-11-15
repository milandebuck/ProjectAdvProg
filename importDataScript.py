#make connection, db, collection for en-nl
from pymongo import MongoClient

client = MongoClient()
collection = client.TeamMartini.entries

#store words in collection
text_file = open("./data/output.txt","r")
count = 0
wordNl = ""
wordEn = ""
for line in text_file.readlines():
	count += 1
	if (count % 2) == 1:
		wordNl = line
	else:
		wordEn = line
		entry = {"en": wordEn,
			  "nl": wordNl}
		collection.insert(entry)