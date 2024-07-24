import { Outlet } from "react-router-dom";


export default function Layout()
{
	return(
		<>
			<div className="w-screen h-screen flex border-none">
			<h1>Spring Blog</h1>
				<div className="w-full">
					<Outlet/>
				</div>
			</div>
		</>
	);
}
