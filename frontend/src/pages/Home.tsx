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
    e.preventDefault(); //stops submit from happening

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
                className="h-full w-full rounded-t pb-6"
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
              <div className="flex w-1/2 pl-4 text-sm">
                <ul className="list-reset flex justify-between flex-1 md:flex-none items-center">
                  <li className="mr-2">
                    <a
                      className="inline-block py-2 px-2 text-white no-underline hover:underline"
                      href="post.html"
                    >
                      POST
                    </a>
                  </li>
                  <li className="mr-2">
                    <a
                      className="inline-block text-gray-600 no-underline hover:text-gray-200 hover:underline py-2 px-2"
                      href="multimenu post.html"
                    >
                      MULTIMENU POST
                    </a>
                  </li>
                  <li className="mr-2">
                    <a
                      className="inline-block text-gray-600 no-underline hover:text-gray-200 hover:underline py-2 px-2"
                      href="#"
                    >
                      LINK
                    </a>
                  </li>
                  <li className="mr-2">
                    <a
                      className="inline-block text-gray-600 no-underline hover:text-gray-200 hover:underline py-2 px-2"
                      href="post_vue.html"
                    >
                      POST_VUE
                    </a>
                  </li>
                </ul>
              </div>

              <div className="flex w-1/2 justify-end content-center">
                <a
                  className="inline-block text-gray-500 no-underline hover:text-white hover:text-underline text-center h-10 p-2 md:h-auto md:p-4 avatar"
                  data-tippy-content="@twitter_handle"
                  href="https://twitter.com/intent/tweet?url=#"
                >
                  <svg
                    className="fill-current h-4"
                    xmlns="http://www.w3.org/2000/svg"
                    viewBox="0 0 32 32"
                  >
                    <path d="M30.063 7.313c-.813 1.125-1.75 2.125-2.875 2.938v.75c0 1.563-.188 3.125-.688 4.625a15.088 15.088 0 0 1-2.063 4.438c-.875 1.438-2 2.688-3.25 3.813a15.015 15.015 0 0 1-4.625 2.563c-1.813.688-3.75 1-5.75 1-3.25 0-6.188-.875-8.875-2.625.438.063.875.125 1.375.125 2.688 0 5.063-.875 7.188-2.5-1.25 0-2.375-.375-3.375-1.125s-1.688-1.688-2.063-2.875c.438.063.813.125 1.125.125.5 0 1-.063 1.5-.25-1.313-.25-2.438-.938-3.313-1.938a5.673 5.673 0 0 1-1.313-3.688v-.063c.813.438 1.688.688 2.625.688a5.228 5.228 0 0 1-1.875-2c-.5-.875-.688-1.813-.688-2.75 0-1.063.25-2.063.75-2.938 1.438 1.75 3.188 3.188 5.25 4.25s4.313 1.688 6.688 1.813a5.579 5.579 0 0 1 1.5-5.438c1.125-1.125 2.5-1.688 4.125-1.688s3.063.625 4.188 1.813a11.48 11.48 0 0 0 3.688-1.375c-.438 1.375-1.313 2.438-2.563 3.188 1.125-.125 2.188-.438 3.313-.875z"></path>
                  </svg>
                </a>
                <a
                  className="inline-block text-gray-500 no-underline hover:text-white hover:text-underline text-center h-10 p-2 md:h-auto md:p-4 avatar"
                  data-tippy-content="#facebook_id"
                  href="https://www.facebook.com/sharer/sharer.php?u=#"
                >
                  <svg
                    className="fill-current h-4"
                    xmlns="http://www.w3.org/2000/svg"
                    viewBox="0 0 32 32"
                  >
                    <path d="M19 6h5V0h-5c-3.86 0-7 3.14-7 7v3H8v6h4v16h6V16h5l1-6h-6V7c0-.542.458-1 1-1z"></path>
                  </svg>
                </a>
              </div>
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
