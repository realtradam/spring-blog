
export default function Register () {
	return(
		<>
<div className="flex flex-col items-center justify-center bg-white p-12">
    <div className="text-xl w-full text-center mb-8 p-4 bg-black text-red-500">Username or Email already exists</div>
    <form role="form" method="post" className="w-full max-w-lg">
        <div className="flex flex-wrap -mx-3 mb-6">
            <div className="w-full md:w-1/2 px-3 mb-6 md:mb-0">
                <label className="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2"
                       >
                    Title
                </label>
                <input className="appearance-none block w-full bg-gray-200 text-gray-700 border rounded py-3 px-4 mb-3 leading-tight focus:outline-none focus:bg-white"
                       id="username"
                       type="text"
                       name="username"
                       placeholder="Ted"/>
                <p className="text-red-500 text-xs italic">Please fill out this field.</p>
            </div>
            <div className="w-full md:w-1/2 px-3">
                <label className="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2">
                    Email
                </label>
                <input className="appearance-none block w-full bg-gray-200 text-gray-700 border border-gray-200 rounded py-3 px-4 leading-tight focus:outline-none focus:bg-white focus:border-gray-500"
                       id="email"
                       type="text"
                       name="email"
                       placeholder="contact@example.com"/>
                <p className="text-red-500 text-xs italic">Please fill out this field.</p>
            </div>
        </div>
        <div className="flex flex-wrap -mx-3 mb-6">
            <div className="w-full px-3">
                <label className="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2">
                    Password
                </label>
                <input className="appearance-none block w-full bg-gray-200 text-gray-700 border border-gray-200 rounded py-3 px-4 leading-tight focus:outline-none focus:bg-white focus:border-gray-500"
                       id="password"
                       type="password"
                       name="password"
                       placeholder="Doe"/>
                <p className="text-red-500 text-xs italic">Please fill out this field.</p>
            </div>
        </div>
        <div className="flex flex-wrap mb-2">
        </div>
        <button type="submit" className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded">Register</button>

    </form>
</div>
		</>
	);

}
