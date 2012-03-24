#!/bin/sh

while true; do
    java -Xms1024M -Xmx1024M -jar CanaryMod.jar nogui

    echo 'Starting Backup Sequence.'
    echo '(Press control+c if you would like to stop the server.)'
    sleep 5
    echo STARTING BACKUP.
    sleep 5
    echo '*************************************'
    echo '** BACKING UP MAP                  **'
    echo '*************************************'

    if [ ! -d backup ]; then
	mkdir backup;
    fi

	name=backup/world-`date "+%m-%d-%Y-%H-%M-%S"`
	zip -r $name.zip world

	echo '-------------------------------------'
	echo
	echo 'BACKUP COMPLETE. RESTARTING SERVER...'
	echo ''
	echo '-------------------------------------'
	sleep 2
done
