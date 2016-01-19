FROM python:latest

RUN pip install rethinkdb

RUN pip install bottle

RUN mkdir /app
WORKDIR /app
COPY ./belayer.py /app

EXPOSE 9090

CMD [ "python" ,  "./belayer.py" ]