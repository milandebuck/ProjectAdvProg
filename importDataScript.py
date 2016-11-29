#make connection, db, collection for en-nl
from pymongo import MongoClient

client = MongoClient("mongodb://adminUser:Azerty123@ds113958.mlab.com:13958/teammartini")
collection = client.teammartini.entries

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

#store users in collection
#user : {
#	"username":,
#	"password":,
#}
#collection.insert(user)