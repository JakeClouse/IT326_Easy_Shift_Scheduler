import { useAuth } from '../context/AuthContext';
import '../components/CSS/Layout.css';
import EmployeeSchedule from './EmployeeSchedule';
import EmployerSchedule from './EmployerSchedule';

export default function Schedule() {
  const { isEmployer } = useAuth();
  return isEmployer ? <EmployerSchedule /> : <EmployeeSchedule />;
}
