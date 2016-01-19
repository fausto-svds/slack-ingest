from bottle import template, route, run
import rethinkdb as r
import json



conn = r.connect(host='192.168.99.100', port=32772, db='test', auth_key='', timeout=20)

def getdocs():
    selection = list(r.table('table1').run(conn))
    return json.dumps(selection)


@route('/belay/hello')
def index():
    return getdocs()


@route('/belay/reactions')
def most_reactions():
	return "hello!"

run(host='0.0.0.0', port=9090)





