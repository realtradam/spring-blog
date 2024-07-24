import { useState, useEffect } from "react";

export default function Home() {
	const [articles, setArticles] = useState<any[]>([]);

	useEffect(() => {
		const url = `${import.meta.env.VITE_API_TITLE}/articles`;
		fetch(url).then((response) => {
			if (response.ok) {
				return response.json();
			}
			throw new Error("Network response was not ok.");
		}).then((response) => setArticles(response));
	}, []);

	const allArticles = articles.map((article) => (
		<div className="border border-red-500 m-1">
		<div>{article.title}</div>
		<div>{article.content}</div>
		</div>
	));
	return(
		<>
			<div className="flex justify-center p-4 flex-col justify-center">
				<h1 className="font-title text-6xl">Welcome to Spring Blog</h1>
				<div className="flex flex-col border border-red-500 mt-4">
				{ allArticles }
				</div>
			</div>
		</>
	);
}
