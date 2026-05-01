import { useNavigate } from 'react-router-dom';
import { useEffect } from 'react';
import { useAuth } from '../context/AuthContext';

export default function CreateSchedule() {
  const { isEmployer } = useAuth();
  const navigate = useNavigate();

  useEffect(() => {
    if (!isEmployer) navigate('/dashboard');
    else navigate('/schedule');
  }, [isEmployer, navigate]);

  return null;
}
