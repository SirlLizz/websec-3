import './App.css';
import Register from "./pages/Register";
import {BrowserRouter, Route,  Routes} from "react-router-dom";
import NavBar from "./components/NavBar";
import NoMatch from "./pages/NoMatch";
import Home from "./pages/Home";
import Profile from "./pages/Profile";
import Feed from "./pages/Feed";

function App() {
  return (
    <div className="App">
        <BrowserRouter>
            <header className="App-header">
                <NavBar/>
            </header>
            <Routes>
                <Route path="/" element={<Home />} />
                <Route path="/signup" element={<Register />} />
                <Route path="/feed" element={<Feed />} />
                <Route path="/profile" element={<Profile />} />
                <Route path="*" element={<NoMatch />} />
            </Routes>
        </BrowserRouter>
    </div>
  );
}

export default App;
