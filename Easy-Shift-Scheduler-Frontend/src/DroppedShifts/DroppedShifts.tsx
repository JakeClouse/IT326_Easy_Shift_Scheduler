import { useState } from 'react';
import { useAuth } from '../context/AuthContext';
import { getDroppedShifts } from '../api/droppedShifts';
import { pickupShift } from '../api/schedule';
import { Link } from 'react-router-dom';
import '../components/CSS/Layout.css';
import './CSS/DroppedShifts.css';

export default function DroppedShifts() {
  const { user, isEmployer } = useAuth();
  const [shifts, setShifts] = useState<string | null>(null);
  const [shiftID, setShiftID] = useState('');
  const [status, setStatus] = useState('');
  const [loading, setLoading] = useState(false);

  async function fetchShifts() {
    setLoading(true);
    setStatus('');
    try {
      const data = await getDroppedShifts();
      setShifts(data);
    } catch (err) {
      setStatus(err instanceof Error ? err.message : 'Failed to load shifts.');
    } finally {
      setLoading(false);
    }
  }

  async function handlePickup(e: React.FormEvent) {
    e.preventDefault();
    if (!user || !shiftID) return;
    setLoading(true);
    setStatus('');
    try {
      const msg = await pickupShift(user.id, Number(shiftID));
      setStatus(msg);
      setShiftID('');
    } catch (err) {
      setStatus(err instanceof Error ? err.message : 'Failed to pickup shift.');
    } finally {
      setLoading(false);
    }
  }

  return (
    <div className="page-container">
      <h1 className="page-title">Dropped Shifts</h1>

      <div className="card">
        <h2 className="card-title">Available Shifts</h2>
        <p style={{ fontSize: 14, color: '#5a7896', marginTop: 0 }}>
          Shifts dropped by other employees that are available for pickup.
        </p>
        <button className="btn btn-primary" onClick={fetchShifts} disabled={loading}>
          {loading ? 'Loading...' : 'Load Available Shifts'}
        </button>

        {shifts !== null && (
          <div style={{ marginTop: 16, padding: 14, background: '#a8c8e8', borderRadius: 6, fontSize: 14, color: '#384959' }}>
            <strong>Dropped Shifts:</strong>
            <pre style={{ margin: '8px 0 0', whiteSpace: 'pre-wrap', wordBreak: 'break-word', fontFamily: 'inherit' }}>
              {shifts || 'No dropped shifts available.'}
            </pre>
          </div>
        )}
      </div>

      {!isEmployer && (
        <div className="card">
          <h2 className="card-title">Pick Up a Shift</h2>
          <p style={{ fontSize: 14, color: '#5a7896', marginTop: 0 }}>
            Enter the shift ID from the list above to claim it.
          </p>
          <form onSubmit={handlePickup}>
            <div className="form-group">
              <label className="form-label" htmlFor="shiftID">Shift ID</label>
              <input
                id="shiftID"
                className="form-input"
                type="number"
                placeholder="Enter shift ID"
                value={shiftID}
                onChange={e => setShiftID(e.target.value)}
                required
              />
            </div>
            <button className="btn btn-primary" type="submit" disabled={loading || !shiftID}>
              {loading ? 'Processing...' : 'Pick Up Shift'}
            </button>
          </form>
        </div>
      )}

      {!isEmployer && (
        <div className="card">
          <h2 className="card-title">Drop a Shift</h2>
          <p style={{ fontSize: 14, color: '#5a7896', marginTop: 0 }}>
            Need coverage for one of your shifts? Drop it here.
          </p>
          <Link to="/drop-shift" className="btn btn-secondary" style={{ textDecoration: 'none', display: 'inline-block' }}>
            Drop a Shift
          </Link>
        </div>
      )}

      {status && (
        <div className={`status-message ${status.toLowerCase().includes('error') || status.toLowerCase().includes('not found') ? 'error' : 'success'}`} style={{ marginTop: 0 }}>
          {status}
        </div>
      )}
    </div>
  );
}
