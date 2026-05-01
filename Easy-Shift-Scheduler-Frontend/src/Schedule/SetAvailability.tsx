import { useState } from 'react';
import { useAuth } from '../context/AuthContext';
import { setAvailSchedule } from '../api/schedule';
import '../components/CSS/Layout.css';
import './CSS/Schedule.css';

export default function SetAvailability() {
  const { user } = useAuth();
  const [times, setTimes] = useState<string[]>(['', '']);
  const [status, setStatus] = useState('');
  const [loading, setLoading] = useState(false);

  function addTime() {
    setTimes(t => [...t, '', '']);
  }

  function removeEntry(startIdx: number) {
    setTimes(t => t.filter((_, i) => i !== startIdx && i !== startIdx + 1));
  }

  function setTime(i: number, val: string) {
    setTimes(t => t.map((v, idx) => idx === i ? val : v));
  }

  async function handleSubmit(e: React.FormEvent) {
    e.preventDefault();
    if (!user) return;
    const filtered = times.filter(Boolean);
    if (filtered.length % 2 !== 0) {
      setStatus('Availability must have an even number of entries (start and end pairs).');
      return;
    }
    setStatus('');
    setLoading(true);
    try {
      const msg = await setAvailSchedule(user.id, {
        id: 0,
        availability_schedule: filtered,
      });
      setStatus(msg);
    } catch (err) {
      setStatus(err instanceof Error ? err.message : 'Failed to update availability.');
    } finally {
      setLoading(false);
    }
  }

  const pairs: [number, number][] = [];
  for (let i = 0; i < times.length - 1; i += 2) {
    pairs.push([i, i + 1]);
  }

  return (
    <div className="page-container">
      <h1 className="page-title">Set Availability</h1>

      <div className="card">
        <h2 className="card-title">Update Availability Schedule</h2>
        <p style={{ fontSize: 14, color: '#5a7896', marginTop: 0, marginBottom: 16 }}>
          Add pairs of start and end times to indicate when you are available to work.
          Each row is one availability window.
        </p>

        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label className="form-label">Availability Windows</label>
            <div className="schedule-times">
              {pairs.map(([startIdx, endIdx], pairIdx) => (
                <div key={pairIdx} style={{ display: 'flex', gap: 8, alignItems: 'center', marginBottom: 4 }}>
                  <div style={{ flex: 1 }}>
                    <div style={{ fontSize: 12, color: '#5a7896', marginBottom: 4 }}>Start</div>
                    <input
                      type="datetime-local"
                      className="schedule-time-entry"
                      style={{ width: '100%', height: 40, borderRadius: 6, border: '1px solid #384959', padding: '0 10px', fontFamily: 'inherit', fontSize: 14 }}
                      value={times[startIdx]}
                      onChange={e => setTime(startIdx, e.target.value)}
                    />
                  </div>
                  <div style={{ flex: 1 }}>
                    <div style={{ fontSize: 12, color: '#5a7896', marginBottom: 4 }}>End</div>
                    <input
                      type="datetime-local"
                      style={{ width: '100%', height: 40, borderRadius: 6, border: '1px solid #384959', padding: '0 10px', fontFamily: 'inherit', fontSize: 14 }}
                      value={times[endIdx]}
                      onChange={e => setTime(endIdx, e.target.value)}
                    />
                  </div>
                  {pairs.length > 1 && (
                    <button
                      type="button"
                      className="remove-time-btn"
                      style={{ marginTop: 20 }}
                      onClick={() => removeEntry(startIdx)}
                    >
                      ×
                    </button>
                  )}
                </div>
              ))}
            </div>
            <button type="button" className="add-time-btn" onClick={addTime}>
              + Add Availability Window
            </button>
          </div>

          <button className="btn btn-primary" type="submit" disabled={loading}>
            {loading ? 'Saving...' : 'Save Availability'}
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
