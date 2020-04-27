from collections import OrderedDict

import requests

SEARCH_TERM = input("Enter a search term: ")

response = requests.get(f'http://api.conceptnet.io/query?node=/c/en/{SEARCH_TERM}&other=/c/en&limit=100')
obj = response.json()

relations = {}

for edge in obj['edges']:
    start = edge['start']['label']
    end = edge['end']['label']
    weight = edge['weight']

    if start != end:
        relations[start] = weight
    relations[end] = weight

sortedRelations = {k: v for k, v in sorted(relations.items(), key=lambda item: item[1], reverse=True)}

print([f"{weight}:{relation}" for relation, weight in sortedRelations.items() if weight > 1])
