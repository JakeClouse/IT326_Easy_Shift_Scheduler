import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { employeeSignUp, employerSignUp } from '../api/auth';
import './CSS/RegisterPage.css';

export default function RegisterPage() {
  const [username, setUsername] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [role, setRole] = useState<'employee' | 'employer'>('employee');
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');
  const [loading, setLoading] = useState(false);

  const navigate = useNavigate();

  async function handleSubmit(e: React.FormEvent) {
    e.preventDefault();
    setError('');
    setSuccess('');
    setLoading(true);
    try {
      const fn = role === 'employer' ? employerSignUp : employeeSignUp;
      const msg = await fn({ username, email, password });
      if (msg.toLowerCase().includes('error') || msg.toLowerCase().includes('already')) {
        setError(msg);
      } else {
        setSuccess('Account created! Redirecting to login...');
        setTimeout(() => navigate('/login'), 1500);
      }
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Registration failed.');
    } finally {
      setLoading(false);
    }
  }

  return (
    <div className="register-bg">
      <div className="register-card">
        <h1 className="register-title">Create Account</h1>
        <p className="register-subtitle">Join Easy Shift Scheduler</p>

        {error && <div className="register-error">{error}</div>}
        {success && <div className="register-success">{success}</div>}

        <form className="register-form" onSubmit={handleSubmit}>
          <div className="register-field">
            <label htmlFor="username">Username</label>
            <input
              id="username"
              type="text"
              placeholder="Choose a username"
              value={username}
              onChange={e => setUsername(e.target.value)}
              required
            />
          </div>

          <div className="register-field">
            <label htmlFor="email">Email</label>
            <input
              id="email"
              type="email"
              placeholder="your@email.com"
              value={email}
              onChange={e => setEmail(e.target.value)}
              required
            />
          </div>

          <div className="register-field">
            <label htmlFor="password">Password</label>
            <input
              id="password"
              type="password"
              placeholder="Choose a strong password"
              value={password}
              onChange={e => setPassword(e.target.value)}
              required
            />
          </div>

          <div className="register-field">
            <label htmlFor="role">Account Type</label>
            <select
              id="role"
              value={role}
              onChange={e => setRole(e.target.value as 'employee' | 'employer')}
            >
              <option value="employee">Employee</option>
              <option value="employer">Employer</option>
            </select>
            <span className="role-hint">
              {role === 'employer'
                ? 'Employer accounts can create schedules and manage staff.'
                : 'Employee accounts can view schedules and submit requests.'}
            </span>
          </div>

          <button className="register-submit" type="submit" disabled={loading}>
            {loading ? 'Creating account...' : 'Create Account'}
          </button>
        </form>

        <div className="register-links">
          <Link to="/login">Already have an account? Sign in</Link>
        </div>
      </div>
    </div>
  );
}
