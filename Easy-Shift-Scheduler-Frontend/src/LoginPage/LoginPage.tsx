import '../CSS/LoginPage.css'
import InputSection from "./InputSection.tsx"

export default function LoginPage() {
    return (
        <section className="login-section">
            <h1 className="login-section-title">
                Login
            </h1>
            <InputSection title={"Username"} inputType={"text"} placeholder={"Username"}/>
            <InputSection title={"Password"} inputType={"password"} placeholder={"Password"}/>
        </section>
    )
}