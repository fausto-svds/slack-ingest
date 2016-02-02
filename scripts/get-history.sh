#!/usr/bin/env bash


token=$1
channel=$2

echo "Pulling history for channel: ${channel}"
echo "Using the following token: ${token}"


filename=history
filenum=1
baseurl="https://slack.com/api/channels.history?token=${token}&channel=${channel}&pretty=1"
count=100

fullfile=history.all


url=${baseurl}
until [ $count -lt 100 ]; do

    outputfile=${filename}.${filenum}
    echo "will output to file: ${outputfile}"
    echo "using the following url: ${url}"

    curl -o ${outputfile} ${url}

    count=$(cat ${outputfile} | grep -w "type" | wc -l)
    echo "count is ${count}"

    timestamp=$(cat ${outputfile} | grep -w "ts" | tail -n 1 | tr -d " "  | cut -d ":" -f 2 | cut -d '"' -f 2)
    echo "will now use timestamp as latest: ${timestamp}"

    url="${baseurl}&latest=${timestamp}"

    ((filenum++))


    cat ${outputfile} >> ${fullfile}

done


