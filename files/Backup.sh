#!/bin/sh

if [ ! -d backups ]; then
	mkdir backups;
    fi

	name=backups/world-`date "+%m-%d-%Y-%H-%M-%S"`
	zip -r $name.zip world


