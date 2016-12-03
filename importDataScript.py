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

#store 3 users in collection
#for i in range(0,3):
#	user : {
#		"username": "user" + i,
#		"password": "Azerty123",
#		"teacher": false
#	}
#	collection.insert(user)
#store 2 teachers in collection
#for j in range(0,2):
#	teacher : {
#		"username": "teacher" + i,
#		"password": "Azerty123",
#		"teacher": true
#	}
#	collection.insert(teacher)