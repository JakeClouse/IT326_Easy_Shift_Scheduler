import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import { updateAccountInfo, updatePassword, deleteAccount, getCompensationReport, updateCompensationRate } from '../api/user';
import '../components/CSS/Layout.css';
import './CSS/AccountSettings.css';

export default function AccountSettings() {
  const { user, isEmployer, logout } = useAuth();
  const navigate = useNavigate();

  const [username, setUsername] = useState(user?.username ?? '');
  const [email, setEmail] = useState(user?.email ?? '');
  const [newPassword, setNewPassword] = useState('');
  const [compensationUserID, setCompensationUserID] = useState('');
  const [compensationRate, setCompensationRate] = useState('');
  const [report, setReport] = useState('');
  const [reportUserID, setReportUserID] = useState('');
  const [status, setStatus] = useState('');
  const [loading, setLoading] = useState(false);

  async function handleUpdateAccount(e: React.FormEvent) {
    e.preventDefault();
    if (!user) return;
    setStatus('');
    setLoading(true);
    try {
      const msg = await updateAccountInfo(user.id, { username, email });
      setStatus(msg);
    } catch (err) {
      setStatus(err instanceof Error ? err.message : 'Update failed.');
    } finally {
      setLoading(false);
    }
  }

  async function handleUpdatePassword(e: React.FormEvent) {
    e.preventDefault();
    if (!user || !newPassword) return;
    setStatus('');
    setLoading(true);
    try {
      const msg = await updatePassword(user.id, newPassword);
      setStatus(msg);
      setNewPassword('');
    } catch (err) {
      setStatus(err instanceof Error ? err.message : 'Password update failed.');
    } finally {
      setLoading(false);
    }
  }

  async function handleUpdateCompensation(e: React.FormEvent) {
    e.preventDefault();
    setStatus('');
    setLoading(true);
    try {
      const msg = await updateCompensationRate(Number(compensationUserID), Number(compensationRate));
      setStatus(msg);
    } catch (err) {
      setStatus(err instanceof Error ? err.message : 'Failed to update rate.');
    } finally {
      setLoading(false);
    }
  }

  async function handleGetReport(e: React.FormEvent) {
    e.preventDefault();
    setStatus('');
    setLoading(true);
    try {
      const result = await getCompensationReport(Number(reportUserID || user?.id));
      setReport(result);
    } catch (err) {
      setStatus(err instanceof Error ? err.message : 'Failed to generate report.');
    } finally {
      setLoading(false);
    }
  }

  async function handleDeleteAccount() {
    if (!user) return;
    if (!confirm('Are you sure you want to delete your account? This cannot be undone.')) return;
    setLoading(true);
    try {
      await deleteAccount(user.id);
      logout();
      navigate('/login');
    } catch (err) {
      setStatus(err instanceof Error ? err.message : 'Delete failed.');
      setLoading(false);
    }
  }

  return (
    <div className="page-container">
      <h1 className="page-title">Account Settings</h1>

      <div className="card">
        <h2 className="card-title">Profile</h2>
        <div style={{ marginBottom: 16, padding: 12, background: '#a8c8e8', borderRadius: 6 }}>
          <p style={{ margin: '0 0 4px', fontWeight: 600, color: '#384959' }}>{user?.username}</p>
          <p style={{ margin: 0, fontSize: 13, color: '#5a7896' }}>{user?.email}</p>
          <p style={{ margin: '4px 0 0', fontSize: 13, color: '#5a7896' }}>
            Role: {isEmployer ? 'Employer' : 'Employee'} &nbsp;|&nbsp; ID: {user?.id}
          </p>
        </div>

        <form onSubmit={handleUpdateAccount}>
          <div className="form-group">
            <label className="form-label" htmlFor="username">Username</label>
            <input
              id="username"
              className="form-input"
              type="text"
              value={username}
              onChange={e => setUsername(e.target.value)}
              required
            />
          </div>
          <div className="form-group">
            <label className="form-label" htmlFor="email">Email</label>
            <input
              id="email"
              className="form-input"
              type="email"
              value={email}
              onChange={e => setEmail(e.target.value)}
              required
            />
          </div>
          <button className="btn btn-primary" type="submit" disabled={loading}>
            {loading ? 'Saving...' : 'Update Profile'}
          </button>
        </form>
      </div>

      <div className="card">
        <h2 className="card-title">Change Password</h2>
        <form onSubmit={handleUpdatePassword}>
          <div className="form-group">
            <label className="form-label" htmlFor="newPassword">New Password</label>
            <input
              id="newPassword"
              className="form-input"
              type="password"
              placeholder="Enter a strong new password"
              value={newPassword}
              onChange={e => setNewPassword(e.target.value)}
              required
            />
          </div>
          <button className="btn btn-primary" type="submit" disabled={loading || !newPassword}>
            {loading ? 'Updating...' : 'Change Password'}
          </button>
        </form>
      </div>

      <div className="card">
        <h2 className="card-title">Compensation Report</h2>
        <form onSubmit={handleGetReport}>
          {isEmployer && (
            <div className="form-group">
              <label className="form-label" htmlFor="reportUserID">Employee ID (leave blank for your own)</label>
              <input
                id="reportUserID"
                className="form-input"
                type="number"
                placeholder="Enter employee ID"
                value={reportUserID}
                onChange={e => setReportUserID(e.target.value)}
              />
            </div>
          )}
          <button className="btn btn-secondary" type="submit" disabled={loading}>
            {loading ? 'Generating...' : 'Generate Report'}
          </button>
        </form>
        {report && (
          <div style={{ marginTop: 16, padding: 14, background: '#a8c8e8', borderRadius: 6, fontSize: 14, color: '#384959' }}>
            <strong>Report:</strong>
            <pre style={{ margin: '8px 0 0', whiteSpace: 'pre-wrap', wordBreak: 'break-word', fontFamily: 'inherit' }}>{report}</pre>
          </div>
        )}
      </div>

      {isEmployer && (
        <div className="card">
          <h2 className="card-title">Update Employee Compensation Rate</h2>
          <form onSubmit={handleUpdateCompensation}>
            <div className="form-group">
              <label className="form-label" htmlFor="compUserID">Employee ID</label>
              <input
                id="compUserID"
                className="form-input"
                type="number"
                placeholder="Enter employee ID"
                value={compensationUserID}
                onChange={e => setCompensationUserID(e.target.value)}
                required
              />
            </div>
            <div className="form-group">
              <label className="form-label" htmlFor="compRate">New Rate ($/hr)</label>
              <input
                id="compRate"
                className="form-input"
                type="number"
                step="0.01"
                placeholder="e.g. 15.50"
                value={compensationRate}
                onChange={e => setCompensationRate(e.target.value)}
                required
              />
            </div>
            <button className="btn btn-primary" type="submit" disabled={loading}>
              {loading ? 'Updating...' : 'Update Rate'}
            </button>
          </form>
        </div>
      )}

      {status && (
        <div className={`status-message ${status.toLowerCase().includes('error') || status.toLowerCase().includes('not found') || status.toLowerCase().includes('failed') ? 'error' : 'success'}`}>
          {status}
        </div>
      )}

      <div className="card danger-zone">
        <h2 className="card-title">Danger Zone</h2>
        <p className="danger-description">
          Permanently delete your account and all associated data. This action cannot be undone.
        </p>
        <button className="btn btn-danger" onClick={handleDeleteAccount} disabled={loading}>
          Delete My Account
        </button>
      </div>
    </div>
  );
}
