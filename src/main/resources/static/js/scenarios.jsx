/*
 * Copyright 2016 Aleksey Dobrynin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

let Result = React.createClass({
    render: function () {
        let data = [];
        if (this.props.successful) {
            data = ["glyphicon",
                "glyphicon-ok-circle",
                ("row-" + this.props.successful).toLowerCase()];
        }
        return (
            <span className={data.join(" ")}/>
        );
    }
});

let Action = React.createClass({
    render: function () {
        let data = [];
        return (
            <a className="action" href="#">{this.props.successful == "WAIT" ? "Stop" : "Run"}</a>
        );
    }
});

let Scenario = React.createClass({
    render: function () {
        return (
            <tr className="row-active">
                <td>{this.props.id}</td>
                <td>{this.props.name}</td>
                <td></td>
                <td><Result successful={this.props.successful}/></td>
                <td><Action successful={this.props.successful}/></td>
            </tr>
        );
    }
});

let Scenarios = React.createClass({
    getInitialState: function () {
        return {
            scenarios: [{id: 1, name: "test", successful: "SUCCESS"},
                {id: 2, name: "tester", successful: "FAIL"},
                {id: 3, name: "toster", successful: "WAIT"},
                {id: 4, name: "trost", successful: ""}]
        }
    },
    render: function () {
        return (
            <table className="table">
                <tr>
                    <th>#</th>
                    <th>Name</th>
                    <th>Exec date</th>
                    <th>Result</th>
                    <th>Action</th>
                </tr>
                {
                    this.state.scenarios.map(function (scenario) {
                        return <Scenario
                            key={scenario.id}
                            id={scenario.id}
                            name={scenario.name}
                            successful={scenario.successful}/>;
                    })
                }
            </table>
        );
    }
});

ReactDOM.render(<Scenarios />, document.getElementById("scenarios"));
