import { useState, useEffect } from "react";
import { useParams } from "react-router-dom";

type article = {
  id: number;
  title: string;
  photoUrl: string;
  content: string;
  createdOn: string;
  updatedOn: string;
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
      <div className="text-center pt-16 md:pt-32">
        <p className="text-sm md:text-base text-green-500 font-bold">
          {articleData?.createdOn}
        </p>
        <h1 className="font-bold break-normal text-3xl md:text-5xl">
          {articleData?.title}
        </h1>
      </div>
      <div
        className="container w-full max-w-6xl mx-auto bg-white bg-cover mt-8 rounded"
        style={{ background: `url('${articleData?.photoUrl}'); height: 75vh;` }}
      ></div>
      <div className="container max-w-5xl mx-auto -mt-32">
        <div className="mx-0 sm:mx-6">
          <div className="bg-white w-full p-8 md:p-24 text-xl md:text-2xl text-gray-800 leading-normal">
            <div>{articleData?.content}</div>
          </div>
        </div>
      </div>
    </>
  );
}
