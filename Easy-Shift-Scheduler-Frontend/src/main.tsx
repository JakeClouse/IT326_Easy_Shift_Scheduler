import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import "./CSS/App.css"
import LoginPage from './LoginPage/LoginPage.tsx'

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <LoginPage />
  </StrictMode>,
)
