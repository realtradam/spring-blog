import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { useState } from "react";
import Home from "../pages/Home";
import Layout from "../components/Layout";
import Article from "../pages/articles/Article";
import NewArticle from "../pages/articles/New";
import Register from "../pages/auth/Register";
import Login from "../pages/auth/Login";

type user = {
  set: React.Dispatch<React.SetStateAction<string | null>>;
  value: string | null;
};
type articleSearch = user;

export default function Index() {
  const [user, setUser] = useState<string | null>(null);
  const [articleSearch, setArticleSearch] = useState<string | null>(null);

  const userProp: user = { set: setUser, value: user };
  const articleSearchProp: articleSearch = {
    set: setArticleSearch,
    value: articleSearch,
  };

  return (
    <>
      <Router>
        <Routes>
          <Route
            path="/"
            element={
              <Layout user={userProp} articleSearch={articleSearchProp} />
            }
          >
            <Route index element={<Home articleSearch={articleSearchProp} />} />
            <Route path="/article/:id" element={<Article />} />
            <Route path="/article/new" element={<NewArticle />} />
            <Route path="register" element={<Register />} />
            <Route path="login" element={<Login user={userProp} />} />
          </Route>
        </Routes>
      </Router>
    </>
  );
}
