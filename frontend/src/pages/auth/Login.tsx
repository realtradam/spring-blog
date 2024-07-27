import { FormEvent } from "react";
import { useNavigate } from "react-router-dom";

//type setUser = { setUser: { func: React.Dispatch<React.SetStateAction<string | null>> } };
type user = {
	set: React.Dispatch<React.SetStateAction<string | null>>;
	value: string | null;
};

export default function Login({ user }: { user: user }) {
	const navigate = useNavigate();

	const handleSubmit = async (e: FormEvent<HTMLFormElement>) => {
		e.preventDefault(); //stops submit from happening

		const target = e.target as typeof e.target & {
			username: { value: string };
			email: { value: string };
			password: { value: string };
		};

		const formData = new FormData();
		formData.append("username", target.username.value);
		formData.append("password", target.password.value);

		const response = await fetch(
			`${import.meta.env.VITE_API_TITLE}/api/v1/login`,
			{
				credentials: "include",
				method: "post",
				body: formData,
			},
		).then((res) => {
			if (res.ok) {
				const url = `${import.meta.env.VITE_API_TITLE}/api/v1/profile`;
				fetch(url, {
					credentials: "include",
					method: "get",
				})
					.then((response) => {
						if (response.ok) {
							return response.json();
						}
						throw new Error("Network response was not ok.");
					})
					.then((response) => {
						user.set(response.username);
						console.log("USER:");
						console.log(user);
						console.log(user.value);
						console.log(response.username);
						navigate("/");
					});
			} else {
				console.log(response);
				alert("check console for error");
			}
		});
	};

	return (
		<>
			<div className="flex h-full justify-center bg-white p-12">
				<form onSubmit={handleSubmit} method="post" className="w-full max-w-lg">
					<div className="flex flex-wrap -mx-3 mb-6">
						<div className="w-full md:w-1/2 px-3 mb-6 md:mb-0">
							<label className="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2">
								Title
							</label>
							<input
								className="appearance-none block w-full bg-gray-200 text-gray-700 border rounded py-3 px-4 mb-3 leading-tight focus:outline-none focus:bg-white"
								id="username"
								type="text"
								name="username"
								placeholder="Ted"
							/>
						</div>
					</div>
					<div className="flex flex-wrap -mx-3 mb-6">
						<div className="w-full px-3">
							<label className="block uppercase tracking-wide text-gray-700 text-xs font-bold mb-2">
								Password
							</label>
							<input
								className="appearance-none block w-full bg-gray-200 text-gray-700 border border-gray-200 rounded py-3 px-4 leading-tight focus:outline-none focus:bg-white focus:border-gray-500"
								id="password"
								type="password"
								name="password"
								placeholder="Doe"
							/>
						</div>
					</div>
					<div className="flex flex-wrap mb-2"></div>
					<button
						type="submit"
						value="Log in"
						className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded">
						Log In
					</button>
				</form>
			</div>
		</>
	);
}
