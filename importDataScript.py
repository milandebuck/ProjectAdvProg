#make connection, db, collection for en-nl
from pymongo import Connection
connection = Connection()
db = connection['translator_db']
collection = db['en_nl_list']

#store words in collection
text_file = open("output.txt","r")
count = 0
wordNl = ""
wordEn = ""
for line in text_file.readlines():
	count += 1
	if (count % 2) == 1:
		wordNl = line
	else:
		wordEn = line
		entry{"en": wordEn,
			  "nl": wordNl}
		collection.insert(entry)