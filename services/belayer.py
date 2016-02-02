from bottle import template, route, run, response
import rethinkdb as r
import json

tablename = 'channels'



conn = r.connect(host='192.168.99.100', port=32772, db='test', auth_key='', timeout=20)

def getdocs():
    selection = list(r.table(tablename).run(conn))
    return json.dumps(selection)


@route('/belay/hello')
def index():
    return getdocs()


@route('/belay/reactions')
def most_reactions():
    reactions = r.table('channels').get("technology")['messages'].has_fields('reactions').has_fields('attachments') \
        .pluck(['text' ,{'attachments' : 'from_url'},  'reactions']) \
        .map(lambda doc : {'text' : doc['text'], 'url' : doc['attachments']['from_url'], 'reactions' : doc['reactions']}) \
        .map(lambda doc : {'text' : doc['text'], 'url' : doc['url'][0], 'count' : doc['reactions']['count'] \
             .reduce(lambda left, right : left.add(right))}) \
        .order_by(r.desc('count')).run(conn)

    print(reactions)
    #print("hello")

    #response.content_type = 'application/text ; charset=UTF8'
    reactions_list = [r for r in reactions]        

    return str(reactions_list)

run(host='0.0.0.0', port=9090)





