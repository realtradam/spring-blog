import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Home from "../pages/Home";
import Layout from "../components/Layout";
import Article from "../pages/Article";
import Register from "../pages/Register";
import Login from "../pages/Login";

export default function Index()
{
	return (<>
				<Router>
					<Routes>
						<Route path="/" element = {<Layout/>}>
							<Route index element={<Home />} />
							<Route path="/article/:id" element={<Article />} />
							<Route path="register" element={<Register />} />
							<Route path="login" element={<Login />} />
						</Route>
					</Routes>
				</Router>
			</>);
}
