import { Outlet } from "react-router-dom";
import { useState, useEffect } from "react";


export default function Layout()
{
	const [user, setUser] = useState<string | null>(null);

	useEffect(() => {
		const url = `${import.meta.env.VITE_API_TITLE}/api/v1/profile`;
		fetch(url, {
			credentials: 'include',
			method: 'get',
		}).then((response) => {
			if (response.ok) {
				return response.json();
			}
			throw new Error("Network response was not ok.");
		}).then((response) => { setUser(response.username); console.log(response.username) });
	}, [user]);

	const logout = () => {
		fetch(`${import.meta.env.VITE_API_TITLE}/api/v1/logout`, {
			credentials: 'include',
			method: 'get',
		}).then(() => {
			setUser(null);
		});
	}

	return(
		<>
<div className="w-full bg-gray-200 min-h-screen font-sans leading-normal tracking-normal">

<nav className="bg-gray-900 p-4 mt-0 w-full">
    <div className="container mx-auto flex items-center">
        <div className="flex text-white font-extrabold">
{/*th:if="${(user == null || user.username == null)}"*/}
<a className="flex text-white text-base no-underline whitespace-nowrap hover:text-white hover:no-underline" href="#">☕ { user === null ? "Spring!" : user }<span  className="hidden w-0 md:w-auto md:block pl-1"></span></a>
        </div>
        <div className="flex pl-4 text-sm place-content-between w-full">
            <ul className="list-reset flex justify-between flex-1 md:flex-none items-center">
                <li className="mr-2">
                    <a className="inline-block py-2 px-2 text-white no-underline" href="/">HOME</a>
                </li>
                <li className="mr-2">
                    <a className="inline-block text-indigo-200 no-underline hover:text-gray-100 hover:text-underline py-2 px-2" href="/article/new">NEW</a>
                </li>
                <li className="mr-2">
                    <a className="inline-block text-indigo-200 no-underline hover:text-indigo-100 hover:text-underline py-2 px-2" href="/register">REGISTER</a>
                </li>
                <li className="mr-2">{ user === null ? 
                    <a  className="inline-block text-indigo-200 no-underline hover:text-indigo-100 hover:text-underline py-2 px-2" href="/login">LOGIN</a> :
                    <button className="inline-block text-indigo-200 no-underline hover:text-indigo-100 hover:text-underline py-2 px-2" onClick={logout} >LOGOUT</button> }
                </li>
            </ul>
			{/*th:action="@{/articles/search}"*/}
            <form  className="w-full max-w-md">
                <div className="flex flex-wrap -mx-3">
                    <div className="w-full px-3">
                        <input className="appearance-none block w-full bg-gray-200 text-gray-700 border rounded py-3 px-4 leading-tight focus:outline-none focus:bg-white"
                               id="search"
                               type="search"
                               name="search"
                               placeholder="Search"/>
                    </div>
                </div>
            </form>
        </div>
    </div>
</nav>

{/*slide in nav*/}
<div id="header" className="bg-white fixed w-full z-10 top-0 hidden animated" style={{opacity: '.95'}}>
    <div className="bg-white">
        <div className="flex flex-wrap items-center content-center">
            <div className="flex w-1/2 justify-start text-white font-extrabold">
                <a className="flex text-gray-100 no-underline hover:text-gray-900 hover:no-underline pl-2" href="#">
                    👻 <span className="hidden w-0 md:w-auto md:block pl-1">Ghostwind CSS</span>
                </a>
            </div>
            <div className="flex w-1/2 justify-end content-center">
                <p className="hidden sm:block mr-3 text-center h-14 p-4 text-xs"><span className="pr-2">Share this</span> 👉</p>
                <a className="inline-block text-white no-underline hover:text-white hover:text-underline text-center h-10 w-10 p-2 md:h-auto md:w-16 md:p-4" href="https://twitter.com/intent/tweet?url=#" style={{backgroundColor: '#33b1ff'}}>
                    <svg className="fill-current text-white h-4" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 32 32"><path d="M30.063 7.313c-.813 1.125-1.75 2.125-2.875 2.938v.75c0 1.563-.188 3.125-.688 4.625a15.088 15.088 0 0 1-2.063 4.438c-.875 1.438-2 2.688-3.25 3.813a15.015 15.015 0 0 1-4.625 2.563c-1.813.688-3.75 1-5.75 1-3.25 0-6.188-.875-8.875-2.625.438.063.875.125 1.375.125 2.688 0 5.063-.875 7.188-2.5-1.25 0-2.375-.375-3.375-1.125s-1.688-1.688-2.063-2.875c.438.063.813.125 1.125.125.5 0 1-.063 1.5-.25-1.313-.25-2.438-.938-3.313-1.938a5.673 5.673 0 0 1-1.313-3.688v-.063c.813.438 1.688.688 2.625.688a5.228 5.228 0 0 1-1.875-2c-.5-.875-.688-1.813-.688-2.75 0-1.063.25-2.063.75-2.938 1.438 1.75 3.188 3.188 5.25 4.25s4.313 1.688 6.688 1.813a5.579 5.579 0 0 1 1.5-5.438c1.125-1.125 2.5-1.688 4.125-1.688s3.063.625 4.188 1.813a11.48 11.48 0 0 0 3.688-1.375c-.438 1.375-1.313 2.438-2.563 3.188 1.125-.125 2.188-.438 3.313-.875z"></path></svg>
                </a>
                <a className="inline-block text-white no-underline hover:text-white hover:text-underline text-center h-10 w-10 p-2 md:h-auto md:w-16 md:p-4" href="https://www.facebook.com/sharer/sharer.php?u=#" style={{backgroundColor: '#005e99'}}>
                    <svg className="fill-current text-white h-4" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 32 32"><path d="M19 6h5V0h-5c-3.86 0-7 3.14-7 7v3H8v6h4v16h6V16h5l1-6h-6V7c0-.542.458-1 1-1z"></path></svg>
                </a>
            </div>
        </div>

    </div>
	{/*Progress bar*/}
    <div id="progress" className="h-1 bg-white shadow" style={{background: "linear-gradient(to right, #4dc0b5 var(--scroll), transparent 0)"}}></div>
</div>

{/*Header*/}
<div className="w-full m-0 p-0 bg-cover bg-bottom" style={{backgroundImage: "url('https://upload.wikimedia.org/wikipedia/commons/6/65/Toronto_Skyline_Summer_2020.jpg')", height: '60vh', maxHeight: '460px'}}>
    <div className="container max-w-4xl bg-black bg-opacity-50 pb-16 rounded-b-xl mx-auto pt-16 md:pt-32 text-center break-normal">
		{/*Title*/}
        <p className="text-white font-extrabold text-3xl md:text-5xl">
            ☕ Spring!
        </p>
        <p className="text-xl md:text-2xl text-gray-200">Welcome to my Blog</p>
    </div>
</div>

<Outlet/>

<footer className="bg-gray-900">
    <div className="container max-w-6xl mx-auto flex items-center px-2 py-8">

        <div className="w-full mx-auto flex flex-wrap items-center">
            <div className="flex w-full md:w-1/2 justify-center md:justify-start text-white font-extrabold">
                <a className="text-gray-900 no-underline hover:text-gray-900 hover:no-underline" href="#">
                    <span className="text-base text-gray-200"></span>
                </a>
            </div>
            <div className="flex w-full pt-2 content-center justify-between md:w-1/2 md:justify-end">
                <ul className="list-reset flex justify-center flex-1 md:flex-none items-center">
                    <li>
                        <a className="inline-block py-2 px-3 text-white no-underline" href="#"></a>
                    </li>
                    <li>
                        <a className="inline-block text-gray-600 no-underline hover:text-gray-200 hover:underline py-2 px-3" href="#"></a>
                    </li>
                    <li>
                        <a className="inline-block text-gray-600 no-underline hover:text-gray-200 hover:underline py-2 px-3" href="#"></a>
                    </li>
                    <li>
                        <a className="inline-block text-gray-600 no-underline hover:text-gray-200 hover:underline py-2 px-3" href="#"></a>
                    </li>
                </ul>
            </div>
        </div>



    </div>
</footer>

<script src="https://unpkg.com/@popperjs/core@2"></script>
<script src="https://unpkg.com/tippy.js@6"></script>
<script>
    //Init tooltips
    tippy('.avatar')
</script>
</div>
		</>
	);
}
