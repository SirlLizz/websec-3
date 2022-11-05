import './App.css';
import Register from "./pages/Register/Register";
import {BrowserRouter, Route,  Routes} from "react-router-dom";
import NavBar from "./components/NavBar/NavBar";
import NoMatch from "./pages/NoMatch";
import Home from "./pages/Home/Home";
import Profile from "./pages/Profile/Profile";
import Feed from "./pages/Feed/Feed";
import Friends from "./pages/Friends/Friends";

function App() {

    window.onload = function() {
        if(document.cookie.match(new RegExp("(^| )token=([^;]+)"))){
            document.getElementById("navbar-before-login").style.display = "none"
            document.getElementById("navbar-after-login").style.display = "flex"
        }
    };

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
                <Route path="/friends" element={<Friends />} />
                <Route path="*" element={<NoMatch />} />
            </Routes>
        </BrowserRouter>
    </div>
  );
}

export default App;
