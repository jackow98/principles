import React from 'react';
import './output.css';
import SearchFeature from "./components/SearchFeature";
import {Hello} from "./components/Hello";

function App() {
    return (
        <Hello compiler="TypeScript" framework="React" />
        // <SearchFeature/>
    );
}

export default App;
