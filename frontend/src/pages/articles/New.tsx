import { FormEvent } from "react";
import { useNavigate } from "react-router-dom";

export default function NewArticle() {
  const navigate = useNavigate();

  const handleSubmit = async (e: FormEvent<HTMLFormElement>) => {
    e.preventDefault(); //stops submit from happening

    const target = e.target as typeof e.target & {
      title: { value: string };
      photoUrl: { value: string };
      content: { value: string };
    };

    const formData = new FormData();
    formData.append("title", target.title.value);
    formData.append("photoUrl", target.photoUrl.value);
    formData.append("content", target.content.value);

    const response = await fetch(
      `${import.meta.env.VITE_API_TITLE}/api/v1/article/new`,
      {
        credentials: "include",
        method: "post",
        body: formData,
      },
    );
    if (response.ok) {
      navigate("/");
    } else {
      console.log(response);
      alert("check console for error");
    }
  };
  return (
    <>
      <div className="flex justify-center bg-white p-12">
        <form onSubmit={handleSubmit} method="post" className="w-full max-w-lg">
          <div className="flex flex-wrap -mx-3 mb-6">
            <div className="w-full md:w-1/2 px-3 mb-6 md:mb-0">
              <label className="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2">
                Title
              </label>
              <input
                className="appearance-none block w-full bg-gray-200 text-gray-700 rounded py-3 px-4 mb-3 leading-tight focus:outline-none focus:bg-white"
                id="title"
                type="text"
                name="title"
                placeholder="Yep"
              />
            </div>
            <div className="w-full md:w-1/2 px-3">
              <label className="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2">
                Photo URL
              </label>
              <input
                className="appearance-none block w-full bg-gray-200 text-gray-700 border border-gray-200 rounded py-3 px-4 leading-tight focus:outline-none focus:bg-white focus:border-gray-500"
                id="photoUrl"
                type="text"
                name="photoUrl"
                placeholder="Doe"
              />
            </div>
          </div>
          <div className="flex flex-wrap -mx-3 mb-6">
            <div className="w-full px-3">
              <label className="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2">
                Content
              </label>
              <input
                className="appearance-none block w-full bg-gray-200 text-gray-700 border border-gray-200 rounded py-3 px-4 leading-tight focus:outline-none focus:bg-white focus:border-gray-500"
                id="content"
                type="text"
                name="content"
                placeholder="Doe"
              />
            </div>
          </div>
          <div className="flex flex-wrap mb-2"></div>
          <button
            type="submit"
            className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded"
          >
            Create
          </button>
        </form>
      </div>
    </>
  );
}
