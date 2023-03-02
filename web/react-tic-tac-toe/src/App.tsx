import React from 'react';
import logo from './logo.svg';
import './App.css';
// import {outcome} from 'kotlin-js-library-lib'
import {sandbox} from "../../../../npm-publish/sandbox/node/build/packages/js";

// let s = new outcome.Outcome.Success(1) // Object { value: 1 }
//   let f = new outcome.Outcome.Failure(2) // Object { reason: 2 }
//
// const handleOutcome = (o: outcome.Outcome<number, number>) => outcome.fold(o, (s => s + 1), (f => 0))

const foo = () => {
    sandbox.greet({name: 'Node', sureName: 'MPP'})
    return (<p>sandbox</p>)
}


function App() {
  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <p>
          Edit <code>src/App.tsx</code> and save to reload.
        </p>
        <a
          className="App-link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
        >
          Learn React
        </a>
      </header>
      {/*<p>success: {JSON.stringify(s)} failure: {JSON.stringify(f)}</p>*/}
      <p>handle success: {foo()}</p>
      {/*<p>handle failure: {handleOutcome(f)}</p>*/}
    </div>
  );
}

export default App;
