import { useState } from 'react';
import { useAuth } from '../context/AuthContext';
import { dropShift } from '../api/droppedShifts';
import '../components/CSS/Layout.css';
import './CSS/DroppedShifts.css';

export default function DropShift() {
  const { user } = useAuth();
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
      const msg = await dropShift(user.id, { startDate, endDate, reason });
      setStatus(msg);
      if (!msg.toLowerCase().includes('error')) {
        setStartDate('');
        setEndDate('');
        setReason('');
      }
    } catch (err) {
      setStatus(err instanceof Error ? err.message : 'Failed to drop shift.');
    } finally {
      setLoading(false);
    }
  }

  return (
    <div className="page-container">
      <h1 className="page-title">Drop a Shift</h1>

      <div className="card">
        <h2 className="card-title">Request Shift Coverage</h2>
        <p style={{ fontSize: 14, color: '#5a7896', marginTop: 0 }}>
          Fill out the shift you need covered. It will appear in the dropped shifts list
          for other employees to pick up.
        </p>

        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label className="form-label" htmlFor="startDate">Shift Start</label>
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
            <label className="form-label" htmlFor="endDate">Shift End</label>
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
            <label className="form-label" htmlFor="reason">Reason for Dropping</label>
            <textarea
              id="reason"
              className="form-textarea"
              placeholder="Why do you need this shift covered?"
              value={reason}
              onChange={e => setReason(e.target.value)}
              required
            />
          </div>

          <button className="btn btn-primary" type="submit" disabled={loading}>
            {loading ? 'Submitting...' : 'Drop Shift'}
          </button>
        </form>

        {status && (
          <div className={`status-message ${status.toLowerCase().includes('error') ? 'error' : 'success'}`}>
            {status}
          </div>
        )}
      </div>
    </div>
  );
}
