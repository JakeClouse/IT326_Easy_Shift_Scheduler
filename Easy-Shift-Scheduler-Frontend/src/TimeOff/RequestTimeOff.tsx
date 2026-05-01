import { useState } from 'react';
import { useAuth } from '../context/AuthContext';
import { requestTimeOff } from '../api/timeOff';
import '../components/CSS/Layout.css';
import './CSS/TimeOff.css';

export default function RequestTimeOff() {
  const { user, isEmployer } = useAuth();
  const [startDate, setStartDate] = useState('');
  const [endDate, setEndDate] = useState('');
  const [reason, setReason] = useState('');
  const [status, setStatus] = useState('');
  const [loading, setLoading] = useState(false);

  async function handleSubmit(e: React.FormEvent) {
    e.preventDefault();
    if (!user) return;
    setStatus('');
    setLoading(true);
    try {
      const msg = await requestTimeOff(user.id, { startDate, endDate, reason });
      setStatus(msg);
      if (!msg.toLowerCase().includes('error')) {
        setStartDate('');
        setEndDate('');
        setReason('');
      }
    } catch (err) {
      setStatus(err instanceof Error ? err.message : 'Failed to submit request.');
    } finally {
      setLoading(false);
    }
  }

  return (
    <div className="page-container">
      <h1 className="page-title">Request Time Off</h1>

      {isEmployer && (
        <div className="card">
          <p style={{ margin: 0, fontSize: 14, color: '#384959' }}>
            As an employer, you can also{' '}
            <a href="/time-off/manage" style={{ color: '#384959', fontWeight: 600 }}>manage employee requests</a>.
          </p>
        </div>
      )}

      <div className="card">
        <h2 className="card-title">Submit Time Off Request</h2>
        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label className="form-label" htmlFor="startDate">Start Date & Time</label>
            <input
              id="startDate"
              className="form-input"
              type="datetime-local"
              value={startDate}
              onChange={e => setStartDate(e.target.value)}
              required
            />
          </div>

          <div className="form-group">
            <label className="form-label" htmlFor="endDate">End Date & Time</label>
            <input
              id="endDate"
              className="form-input"
              type="datetime-local"
              value={endDate}
              onChange={e => setEndDate(e.target.value)}
              required
            />
          </div>

          <div className="form-group">
            <label className="form-label" htmlFor="reason">Reason</label>
            <textarea
              id="reason"
              className="form-textarea"
              placeholder="Describe the reason for your time off request..."
              value={reason}
              onChange={e => setReason(e.target.value)}
              required
            />
          </div>

          <button className="btn btn-primary" type="submit" disabled={loading}>
            {loading ? 'Submitting...' : 'Submit Request'}
          </button>
        </form>

        {status && (
          <div className={`status-message ${status.toLowerCase().includes('error') || status.toLowerCase().includes('not found') ? 'error' : 'success'}`}>
            {status}
          </div>
        )}
      </div>
    </div>
  );
}
