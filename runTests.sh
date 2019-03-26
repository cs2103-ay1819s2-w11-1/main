#!/bin/bash

./gradlew clean headless allTests checkstyleTest coverage coveralls -i

