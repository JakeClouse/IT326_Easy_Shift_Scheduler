import './CSS/LoginPage.css'
import InputSection from "./InputSection.tsx"
import AccountInformation from "./AccountInformation.tsx"

export default function LoginPage() {
    return (
        <section className="login-section">
            <InputSection title={"Email"} inputType={"text"} placeholder={"Email"}/>
            <InputSection title={"Password"} inputType={"password"} placeholder={"Password"}/>
            <input className={"login-button"} type={"submit"} value={"Login"} />
            <AccountInformation />
        </section>
    )
}