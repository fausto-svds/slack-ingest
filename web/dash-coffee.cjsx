

CategoryContainer = React.createClass
     render: ->
     	<Nav bsStyle='pills' stacked>
             <NavItem title="Reactions">Reactions</NavItem>
             <NavItem title="Stars">Stars</NavItem>
     	</Nav>


ControlContainer = React.createClass
    render: ->
                 <Nav bsStyle='pills'>
                     <NavItem title="Channel">Channel</NavItem>
                 </Nav>


App = React.createClass
    render: ->
        <div container-fluid>
            <ControlContainer/>
            <CategoryContainer/>
        </div>

        