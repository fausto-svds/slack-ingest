var React = require('react')
var ReactDOM = require('react-dom')
var Navbar = require ('react-bootstrap/lib/NavBar')
var NavDropdown = require ('react-bootstrap/lib/NavDropdown')
var MenuItem = require ('react-bootstrap/lib/MenuItem')
var Nav = require('react-bootstrap/lib/Nav')
var NavItem = require('react-bootstrap/lib/NavItem')
var Grid = require('react-bootstrap/lib/Grid')
var Col = require('react-bootstrap/lib/Col')
var Row = require('react-bootstrap/lib/Row')
var ButtonGroup = require('react-bootstrap/lib/ButtonGroup')
var Button = require('react-bootstrap/lib/Button')
var Glyphicon = require('react-bootstrap/lib/Glyphicon')



var ControlContainer = React.createClass({
    render: function() {
        return (
            <Navbar>
                <Navbar.Header>
                  <Navbar.Brand>
                    <a href="#">Belayer</a>
                  </Navbar.Brand>
                </Navbar.Header>
                <Nav>
                  <NavItem eventKey={1} href="#">All</NavItem>
                  <NavDropdown eventKey={3} title="Channels" id="basic-nav-dropdown">
                    <MenuItem eventKey={3.1}>Action</MenuItem>
                    <MenuItem eventKey={3.2}>Another action</MenuItem>
                    <MenuItem eventKey={3.3}>Something else here</MenuItem>
                    <MenuItem divider />
                    <MenuItem eventKey={3.3}>Separated link</MenuItem>
                  </NavDropdown>
                </Nav>
              </Navbar>
        )}})


var CategoryContainer = React.createClass({
    render: function () {
        return (
            <ButtonGroup vertical>
                <Button bsSize='large'>
                    <Glyphicon glyph="star" />Stars
                </Button>
               <Button bsSize='large'>
                    <Glyphicon glyph="heart" />Reactions
               </Button>
            </ButtonGroup>
        )
    }
})

var DashboardContainer = React.createClass({
    render: function() {
        return  (
             <div/>)}})

var App = React.createClass({
    render: function () {
        return (
            <Grid>
                <Row>
                     <Col><ControlContainer/></Col>
                </Row>
                <Row>
                    <Col><CategoryContainer/></Col>
                    <Col componentClass='DashboardContainer'/>
                </Row>
            </Grid>
        )
    }

})

ReactDOM.render(<App/>, document.getElementById('content'))