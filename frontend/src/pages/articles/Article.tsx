import { useState, useEffect } from "react";
import { useParams } from "react-router-dom";

type article = {
  id: number;
  title: string;
  photoUrl: string;
  content: string;
  createdOn: string;
  updateOn: string;
};

export default function Article() {
  const { id } = useParams();
  const [articleData, setArticleData] = useState<article>();

  useEffect(() => {
    const url = `${import.meta.env.VITE_API_TITLE}/api/v1/article/${id}`;
    fetch(url)
      .then((response) => {
        if (response.ok) {
          return response.json();
        }
        throw new Error("Network response was not ok.");
      })
      .then((response) => setArticleData(response)); //.catch(() => navigate("/"));
  }, [id]);

  return (
    <>
      <h1>{articleData?.title}</h1>
      <div>{articleData?.content}</div>
    </>
  );
}
