import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'react-toastify/dist/ReactToastify.css';
import 'filepond/dist/filepond.min.css';

import {Content} from "./components/Content";
import {ToastContainer} from "react-toastify";

function App() {

    return (
        <div className="App">
            <header className="App-header">
                <Content/>
                <ToastContainer/>
            </header>
        </div>
    );
}

export default App;
