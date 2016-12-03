#make connection, db, collection for en-nl
from pymongo import MongoClient

client = MongoClient("mongodb://adminUser:Azerty123@ds113958.mlab.com:13958/teammartini")
client.teammartini.entries.drop()
client.teammartini.users.drop()
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
#for i in range(1,4):
#	user = {
#		"username": "user" + str(i),
#		"password": "Azerty123",
#		"teacher": bool(0)
#	}
#	collection.insert(user)
#store 3 teachers in collection
#for j in range(1,4):
#	teacher = {
#		"username": "teacher" + str(j),
#		"password": "Azerty123",
#		"teacher": bool(1)
#	}
#	collection.insert(teacher)