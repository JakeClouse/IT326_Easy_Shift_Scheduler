import { useState, useEffect } from 'react';
import { useAuth } from '../context/AuthContext';
import { clockIn, clockOut } from '../api/timecard';
import '../components/CSS/Layout.css';
import './CSS/Timecard.css';

export default function ClockInOut() {
  const { user } = useAuth();
  const [reason, setReason] = useState('');
  const [status, setStatus] = useState('');
  const [loading, setLoading] = useState(false);
  const [currentTime, setCurrentTime] = useState(new Date());

  useEffect(() => {
    const timer = setInterval(() => setCurrentTime(new Date()), 1000);
    return () => clearInterval(timer);
  }, []);

  function toLocalISOString(date: Date): string {
    const pad = (n: number) => String(n).padStart(2, '0');
    return `${date.getFullYear()}-${pad(date.getMonth() + 1)}-${pad(date.getDate())}T${pad(date.getHours())}:${pad(date.getMinutes())}:${pad(date.getSeconds())}`;
  }

  async function handleClockIn() {
    if (!user) return;
    setLoading(true);
    setStatus('');
    try {
      const time = toLocalISOString(new Date());
      const msg = await clockIn(user.id, time);
      setStatus(msg);
    } catch (err) {
      setStatus(err instanceof Error ? err.message : 'Failed to clock in.');
    } finally {
      setLoading(false);
    }
  }

  async function handleClockOut(e: React.FormEvent) {
    e.preventDefault();
    if (!user) return;
    setLoading(true);
    setStatus('');
    try {
      const time = toLocalISOString(new Date());
      const msg = await clockOut(user.id, time, reason || 'End of shift');
      setStatus(msg);
      setReason('');
    } catch (err) {
      setStatus(err instanceof Error ? err.message : 'Failed to clock out.');
    } finally {
      setLoading(false);
    }
  }

  return (
    <div className="page-container">
      <h1 className="page-title">Clock In / Out</h1>

      <div className="clock-status">
        <p className="clock-status-time">
          {currentTime.toLocaleTimeString()}
        </p>
        <p className="clock-status-label">
          {currentTime.toLocaleDateString(undefined, { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' })}
        </p>
      </div>

      <div className="card">
        <h2 className="card-title">Clock In</h2>
        <p style={{ fontSize: 14, color: '#5a7896', marginTop: 0 }}>
          Record your start time. Current time will be used automatically.
        </p>
        <button
          className="btn btn-success"
          onClick={handleClockIn}
          disabled={loading}
          style={{ fontSize: 16, padding: '12px 32px' }}
        >
          {loading ? 'Processing...' : 'Clock In Now'}
        </button>
      </div>

      <div className="card">
        <h2 className="card-title">Clock Out</h2>
        <p style={{ fontSize: 14, color: '#5a7896', marginTop: 0 }}>
          Record your end time. Optionally add a reason or note.
        </p>
        <form onSubmit={handleClockOut}>
          <div className="form-group">
            <label className="form-label" htmlFor="reason">Reason / Notes (optional)</label>
            <input
              id="reason"
              className="form-input"
              type="text"
              placeholder="e.g. End of shift, lunch break..."
              value={reason}
              onChange={e => setReason(e.target.value)}
            />
          </div>
          <button
            className="btn btn-danger"
            type="submit"
            disabled={loading}
            style={{ fontSize: 16, padding: '12px 32px' }}
          >
            {loading ? 'Processing...' : 'Clock Out Now'}
          </button>
        </form>
      </div>

      {status && (
        <div className={`status-message ${status.toLowerCase().includes('error') || status.toLowerCase().includes('not found') ? 'error' : 'success'}`}>
          {status}
        </div>
      )}
    </div>
  );
}
