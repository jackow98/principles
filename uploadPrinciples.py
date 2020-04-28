# importing the requests library
import re

import requests

# For production
URL = "https://principles.jackow98.site/Principles"

# For development
# URL = "http://localhost/Principles"

topic = ""
principle = ""
topicCount = 0
parentPrincipleCount = 0
childPrincipleCount = 0
identifier = ""

topicPattern = re.compile("### (.*)")
parentPrinciplePattern = re.compile("- \*\*(.*)\*\*")
childPrinciplePattern = re.compile("[0-9]*\.\s(.*)")

with open('principles.md', 'r', encoding='utf8') as fin:

    for line in fin:
        strippedLine = line.strip().replace("’", "'")

        topicMatch = topicPattern.match(strippedLine)
        parentMatch = parentPrinciplePattern.match(strippedLine)
        childMatch = childPrinciplePattern.match(strippedLine)

        if topicMatch:
            topic = topicMatch.group(1)
            topicCount += 1
            parentPrincipleCount = 0
            childPrincipleCount = 0

        if parentMatch:
            principle = parentMatch.group(1)
            parentPrincipleCount += 1
            childPrincipleCount = 0

        if childMatch:
            principle = childMatch.group(1)
            childPrincipleCount += 1

        if parentMatch or childMatch:
            identifier = f"{topic}.{parentPrincipleCount}.{childPrincipleCount}"
            data = {
                "topic": topic,
                "id": identifier,
                "principleText": principle
            }
            headers = {'charset=utf-8'}

            r = requests.post(url=URL, json=data)
            if r.status_code != 200:
                print(principle + " - Not added")

            # print(f"{identifier}, {principle}")
            principle = ""
