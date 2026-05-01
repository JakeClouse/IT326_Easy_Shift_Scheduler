import { useState } from 'react';
import { Link } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import { getAvailSchedule, acknowledgeSchedule } from '../api/schedule';
import '../components/CSS/Layout.css';
import './CSS/Schedule.css';

export default function EmployeeSchedule() {
  const { user } = useAuth();
  const [schedule, setSchedule] = useState<string | null>(null);
  const [employerID, setEmployerID] = useState('');
  const [status, setStatus] = useState('');
  const [loading, setLoading] = useState(false);

  async function fetchSchedule() {
    if (!user) return;
    setLoading(true);
    setStatus('');
    try {
      const data = await getAvailSchedule(user.id);
      setSchedule(data);
    } catch (err) {
      setStatus(err instanceof Error ? err.message : 'Failed to load schedule.');
    } finally {
      setLoading(false);
    }
  }

  async function handleAcknowledge(e: React.FormEvent) {
    e.preventDefault();
    if (!user || !employerID) return;
    setLoading(true);
    setStatus('');
    try {
      const msg = await acknowledgeSchedule(user.id, Number(employerID));
      setStatus(msg);
    } catch (err) {
      setStatus(err instanceof Error ? err.message : 'Failed to acknowledge.');
    } finally {
      setLoading(false);
    }
  }

  return (
    <div className="page-container">
      <h1 className="page-title">My Schedule</h1>

      <div className="card">
        <h2 className="card-title">Availability Schedule</h2>
        <p style={{ fontSize: 14, color: '#5a7896', marginTop: 0 }}>
          Your availability schedule shows when you are available to work.
        </p>
        <button className="btn btn-primary" onClick={fetchSchedule} disabled={loading}>
          {loading ? 'Loading...' : 'Load My Availability'}
        </button>

        {schedule && (
          <div style={{ marginTop: 16, padding: 14, background: '#a8c8e8', borderRadius: 6, fontSize: 14, color: '#384959' }}>
            <strong>Availability:</strong>
            <pre style={{ margin: '8px 0 0', whiteSpace: 'pre-wrap', wordBreak: 'break-word' }}>{schedule}</pre>
          </div>
        )}

        {status && (
          <div className={`status-message ${status.toLowerCase().includes('error') || status.toLowerCase().includes('not found') ? 'error' : 'success'}`}>
            {status}
          </div>
        )}
      </div>

      <div className="card">
        <h2 className="card-title">Acknowledge Schedule</h2>
        <p style={{ fontSize: 14, color: '#5a7896', marginTop: 0 }}>
          Confirm you have seen and accept your work schedule.
        </p>
        <form onSubmit={handleAcknowledge}>
          <div className="form-group">
            <label className="form-label" htmlFor="employerID">Employer ID</label>
            <input
              id="employerID"
              className="form-input"
              type="number"
              placeholder="Enter your employer's ID"
              value={employerID}
              onChange={e => setEmployerID(e.target.value)}
              required
            />
          </div>
          <button className="btn btn-primary" type="submit" disabled={loading}>
            {loading ? 'Processing...' : 'Acknowledge Schedule'}
          </button>
        </form>
      </div>

      <div className="card">
        <h2 className="card-title">Quick Links</h2>
        <div style={{ display: 'flex', gap: 12, flexWrap: 'wrap' }}>
          <Link to="/availability" className="btn btn-secondary" style={{ textDecoration: 'none' }}>
            Update Availability
          </Link>
          <Link to="/shifts" className="btn btn-secondary" style={{ textDecoration: 'none' }}>
            View Dropped Shifts
          </Link>
        </div>
      </div>
    </div>
  );
}
