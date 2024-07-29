import { useState, useEffect, useCallback, FormEvent } from "react";

type article = {
  id: number;
  title: string;
  photoUrl: string;
  content: string;
  createdBy: string;
  createdOn: string;
  updateOn: string;
};
// if null then code will know to not search and just return all articles
type articleSearch = {
  set: React.Dispatch<React.SetStateAction<string | null>>;
  value: string | null;
};

export default function Home({
  articleSearch,
  username,
}: {
  articleSearch: articleSearch;
  username: string | null;
}) {
  const [articles, setArticles] = useState<article[]>([]);
  const [allArticles, setAllArticles] = useState<JSX.Element[]>([]);

  const handleDelete = async (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    const target = e.target as typeof e.target & {
      id: { value: string };
    };

    const response = await fetch(
      `${import.meta.env.VITE_API_TITLE}/api/v1/articles/delete/${target.id.value}`,
      {
        credentials: "include",
        method: "get",
      },
    );
    if (!response.ok) {
      console.log(response);
      alert("check console for error");
    } else {
      fetchArticles();
    }
  };

  function renderButtons(article: article) {
    return (
      <div>
        <a
          href={`/article/edit/${article.id}`}
          className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-1 px-2 ml-4 text-sm rounded"
        >
          Edit
        </a>
        <form onSubmit={handleDelete} method="post">
          <input type="hidden" name="id" value={article.id} />
          <button className="bg-red-500 hover:bg-red-700 text-white font-bold py-1 px-2 ml-4 text-sm rounded">
            Delete
          </button>
        </form>
      </div>
    );
  }

  const fetchArticles = useCallback(() => {
    let url;
    if (articleSearch.value === null) {
      //alert("not searched");
      url = `${import.meta.env.VITE_API_TITLE}/api/v1/articles`;
    } else {
      //alert("searched");
      url = `${import.meta.env.VITE_API_TITLE}/api/v1/articles?search=${
        articleSearch.value
      }`;
    }
    fetch(url)
      .then((response) => {
        if (response.ok) {
          return response.json();
        }
        throw new Error("Network response was not ok.");
      })
      .then((response) => setArticles(response));
  }, [articleSearch.value]);

  // pull data when new search is given
  useEffect(() => {
    fetchArticles();
  }, [articleSearch.value, fetchArticles]);

  // when new data is pulled update the articles shown
  useEffect(() => {
    setAllArticles(
      articles.map((article) => (
        <div
          key={article.id}
          className="w-full md:w-1/2 p-6 flex flex-col flex-grow flex-shrink"
        >
          <div className="flex-1 bg-white rounded-t rounded-b-none overflow-hidden shadow-lg">
            {/*th:href="@{/articles/{articleId}(articleId=${article.id})}"*/}
            <a
              href={`/article/${article.id}`}
              className="flex flex-wrap no-underline hover:bg-sky-200 border-b-2 hover:no-underline"
            >
              <img
                src={article.photoUrl}
                className="h-full w-full rounded-t pb-6 max-h-72 object-cover"
              />
              <div className="w-full font-bold text-xl text-gray-900 px-6">
                {article.title}
              </div>
              <p className="text-gray-800 font-serif text-base px-6 mb-5"></p>
            </a>
            {username == article?.createdBy && renderButtons(article)}
          </div>
          <div className="flex-none mt-auto bg-white rounded-b rounded-t-none overflow-hidden shadow-lg p-6">
            <div className="flex items-center justify-between">
              <p className="text-gray-600 text-xs md:text-sm"></p>
            </div>
          </div>
        </div>
      )),
    );
  }, [articles, username]);

  return (
    <>
      {/*Container*/}
      <div className="container px-4 md:px-0 max-w-6xl mx-auto -mt-32">
        <div className="mx-0 sm:mx-6">
          {/*<!--Nav-->*/}
          <nav className="mt-0 w-full">
            <div className="container mx-auto flex items-center">
              <div className="flex w-1/2 pl-4 text-sm"></div>

              <div className="flex w-1/2 my-8 justify-end content-center"></div>
            </div>
          </nav>

          <div className="bg-gray-200 w-full text-xl md:text-2xl text-gray-800 leading-normal rounded-t">
            {/*<!--Lead Card-->*/}
            <div
              className="flex h-full bg-contain rounded overflow-hidden shadow-lg"
              style={{
                backgroundImage:
                  "url('/public/abstract-polygonal-banner-background-vector.jpg')",
              }}
            >
              <a
                href="post.html"
                className="flex flex-wrap no-underline hover:no-underline"
              >
                <div className="w-full md:w-2/3 rounded-t"></div>

                <div className="w-full md:w-1/3 flex flex-col flex-grow flex-shrink">
                  <div className="flex-1 bg-white rounded-t rounded-b-none overflow-hidden shadow-lg">
                    <p className="w-full text-gray-600 text-xs md:text-sm pt-6 px-6">
                      Spring Blog
                    </p>
                    <div className="w-full font-bold text-xl text-gray-900 px-6">
                      👋 Welcome to my Java Spring Blog!
                    </div>
                    <p className="text-gray-800 font-serif text-base px-6 mb-5">
                      This is a blog project I have created with the goal of
                      learning and understand the ins and outs of the Java
                      Spring framework.
                    </p>
                  </div>

                  <div className="flex-none mt-auto bg-white rounded-b rounded-t-none overflow-hidden shadow-lg p-6">
                    <div className="flex items-center justify-between">
                      <p className="text-gray-600 text-xs md:text-sm"></p>
                    </div>
                  </div>
                </div>
              </a>
            </div>
            <div className="flex flex-wrap justify-between pt-12 -mx-6">
              {allArticles}
            </div>
          </div>
        </div>
      </div>
    </>
  );
}
