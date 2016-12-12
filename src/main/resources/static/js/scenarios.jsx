let Scenario = React.createClass({
    render: function () {
        return (
            <tr className="row-active">
                <td>{this.props.id}</td>
                <td>{this.props.name}</td>
                <td></td>
                <td></td>
                <td></td>
            </tr>
        );
    }
});

let Scenarios = React.createClass({
    getInitialState: function () {
        return {
            scenarios: [{id: 1, name: "test"}, {id: 2, name: "tester"}]
        }
    },
    render: function () {
        return (
            <table className="table">
                <tr>
                    <th>#</th>
                    <th>name</th>
                    <th>exec date</th>
                    <th>result</th>
                    <th>action</th>
                </tr>
                {
                    this.state.scenarios.map(function (scenario) {
                        return <Scenario
                            key={scenario.id}
                            id={scenario.id}
                            name={scenario.name}/>;
                    })
                }
            </table>
        );
    }
});

ReactDOM.render(<Scenarios />, document.getElementById("scenarios"));
