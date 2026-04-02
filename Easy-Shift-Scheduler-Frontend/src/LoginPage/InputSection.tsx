import "../CSS/InputSection.css"

interface InputSectionProps {
    title: string;
    inputType: string;
    placeholder: string;
}


export default function ({title, inputType, placeholder}: InputSectionProps) {
    return (
        <section className="input-section">
            <h2 className="login-section-title">
                {title}
            </h2>
            <input type={inputType} placeholder={placeholder}/>
        </section>
    )
}