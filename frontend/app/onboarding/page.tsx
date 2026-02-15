"use client";

import { useEffect, useState } from "react";
import { useRouter } from "next/navigation";

type Payday = {
  id?: number;
  amount: string;
  frequency: string;
  startDate: string;
};

type Bill = {
  id?: number;
  name: string;
  amount: string;
  frequency: string;
  startDate: string;
};

export default function Onboarding() {
  const router = useRouter();
  const [step, setStep] = useState(1);
  const [paydays, setPaydays] = useState<Payday[]>([]);
  const [bills, setBills] = useState<Bill[]>([]);
  const [loading, setLoading] = useState(false);

  // Payday form
  const [paydayForm, setPaydayForm] = useState<Payday>({
    amount: "",
    frequency: "biweekly",
    startDate: "",
  });

  // Bill form
  const [billForm, setBillForm] = useState<Bill>({
    name: "",
    amount: "",
    frequency: "monthly",
    startDate: "",
  });

  useEffect(() => {
    const token = localStorage.getItem("token");
    if (!token) {
      router.push("/login");
    }
  }, [router]);

  const addPayday = async () => {
    if (!paydayForm.amount || !paydayForm.startDate) {
      alert("Please fill in all fields");
      return;
    }

    setLoading(true);
    try {
      const token = localStorage.getItem("token");
      const response = await fetch("http://localhost:8080/api/paydays", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify({
          amount: parseFloat(paydayForm.amount),
          frequency: paydayForm.frequency,
          startDate: paydayForm.startDate,
        }),
      });

      const data = await response.json();

      if (!response.ok) {
        console.error("Failed to create payday:", data);
        alert(`Failed to create payday: ${data.message || JSON.stringify(data)}`);
        return;
      }

      setPaydays([...paydays, data]);
      setPaydayForm({ amount: "", frequency: "biweekly", startDate: "" });
    } catch (error) {
      console.error("Error creating payday:", error);
      alert(`Error: ${error instanceof Error ? error.message : "Network error"}`);
    } finally {
      setLoading(false);
    }
  };

  const addBill = async () => {
    if (!billForm.name || !billForm.amount || !billForm.startDate) {
      alert("Please fill in all fields");
      return;
    }

    setLoading(true);
    try {
      const token = localStorage.getItem("token");
      const response = await fetch("http://localhost:8080/api/bills", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify({
          name: billForm.name,
          amount: parseFloat(billForm.amount),
          frequency: billForm.frequency,
          startDate: billForm.startDate,
        }),
      });

      const data = await response.json();

      if (!response.ok) {
        console.error("Failed to create bill:", data);
        alert(`Failed to create bill: ${data.message || JSON.stringify(data)}`);
        return;
      }

      setBills([...bills, data]);
      setBillForm({ name: "", amount: "", frequency: "monthly", startDate: "" });
    } catch (error) {
      console.error("Error creating bill:", error);
      alert(`Error: ${error instanceof Error ? error.message : "Network error"}`);
    } finally {
      setLoading(false);
    }
  };

  const finishOnboarding = () => {
    localStorage.setItem("onboardingComplete", "true");
    router.push("/dashboard");
  };

  return (
    <div className="min-h-screen bg-gray-50">
      <nav className="border-b border-gray-200 bg-white">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="flex justify-between items-center h-16">
            <span className="text-2xl font-bold text-blue-600">Budget Calendar</span>
            <div className="text-sm text-gray-600">
              Step {step} of 3
            </div>
          </div>
        </div>
      </nav>

      <main className="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        {/* Step 1: Paydays */}
        {step === 1 && (
          <div className="bg-white rounded-lg shadow-sm p-8">
            <h2 className="text-2xl font-bold text-gray-900 mb-2">
              Add Your Paydays
            </h2>
            <p className="text-gray-600 mb-6">
              Let's start by adding when you get paid. You can add multiple paydays if you have more than one income source.
            </p>

            <div className="space-y-4 mb-6">
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">
                  Amount
                </label>
                <input
                  type="number"
                  step="0.01"
                  value={paydayForm.amount}
                  onChange={(e) => setPaydayForm({ ...paydayForm, amount: e.target.value })}
                  className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-blue-500 focus:border-blue-500 text-gray-900"
                  placeholder="3000.00"
                />
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">
                  Frequency
                </label>
                <select
                  value={paydayForm.frequency}
                  onChange={(e) => setPaydayForm({ ...paydayForm, frequency: e.target.value })}
                  className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-blue-500 focus:border-blue-500 text-gray-900"
                >
                  <option value="weekly">Weekly</option>
                  <option value="biweekly">Biweekly</option>
                  <option value="monthly">Monthly</option>
                </select>
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">
                  Next Payday
                </label>
                <input
                  type="date"
                  value={paydayForm.startDate}
                  onChange={(e) => setPaydayForm({ ...paydayForm, startDate: e.target.value })}
                  className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-blue-500 focus:border-blue-500 text-gray-900"
                />
              </div>

              <button
                onClick={addPayday}
                disabled={loading}
                className="w-full py-2 px-4 bg-blue-600 text-white rounded-lg hover:bg-blue-700 disabled:bg-gray-400"
              >
                {loading ? "Adding..." : "Add Payday"}
              </button>
            </div>

            {paydays.length > 0 && (
              <div className="mb-6">
                <h3 className="font-semibold text-gray-900 mb-2">Your Paydays:</h3>
                <div className="space-y-2">
                  {paydays.map((payday, index) => (
                    <div key={index} className="p-3 bg-gray-50 rounded-lg">
                      <div className="font-medium">${payday.amount}</div>
                      <div className="text-sm text-gray-600">
                        {payday.frequency} - Starting {payday.startDate}
                      </div>
                    </div>
                  ))}
                </div>
              </div>
            )}

            <div className="flex justify-between">
              <div></div>
              <button
                onClick={() => setStep(2)}
                disabled={paydays.length === 0}
                className="py-2 px-6 bg-blue-600 text-white rounded-lg hover:bg-blue-700 disabled:bg-gray-400"
              >
                Next: Add Bills
              </button>
            </div>
          </div>
        )}

        {/* Step 2: Bills */}
        {step === 2 && (
          <div className="bg-white rounded-lg shadow-sm p-8">
            <h2 className="text-2xl font-bold text-gray-900 mb-2">
              Add Your Bills
            </h2>
            <p className="text-gray-600 mb-6">
              Now let's add your recurring bills and payments. Don't worry, you can always add more later.
            </p>

            <div className="space-y-4 mb-6">
              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">
                  Bill Name
                </label>
                <input
                  type="text"
                  value={billForm.name}
                  onChange={(e) => setBillForm({ ...billForm, name: e.target.value })}
                  className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-blue-500 focus:border-blue-500 text-gray-900"
                  placeholder="Electric Bill"
                />
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">
                  Amount
                </label>
                <input
                  type="number"
                  step="0.01"
                  value={billForm.amount}
                  onChange={(e) => setBillForm({ ...billForm, amount: e.target.value })}
                  className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-blue-500 focus:border-blue-500 text-gray-900"
                  placeholder="150.00"
                />
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">
                  Frequency
                </label>
                <select
                  value={billForm.frequency}
                  onChange={(e) => setBillForm({ ...billForm, frequency: e.target.value })}
                  className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-blue-500 focus:border-blue-500 text-gray-900"
                >
                  <option value="weekly">Weekly</option>
                  <option value="biweekly">Biweekly</option>
                  <option value="monthly">Monthly</option>
                  <option value="quarterly">Quarterly</option>
                  <option value="yearly">Yearly</option>
                </select>
              </div>

              <div>
                <label className="block text-sm font-medium text-gray-700 mb-1">
                  First Due Date
                </label>
                <input
                  type="date"
                  value={billForm.startDate}
                  onChange={(e) => setBillForm({ ...billForm, startDate: e.target.value })}
                  className="w-full px-3 py-2 border border-gray-300 rounded-lg focus:ring-blue-500 focus:border-blue-500 text-gray-900"
                />
              </div>

              <button
                onClick={addBill}
                disabled={loading}
                className="w-full py-2 px-4 bg-blue-600 text-white rounded-lg hover:bg-blue-700 disabled:bg-gray-400"
              >
                {loading ? "Adding..." : "Add Bill"}
              </button>
            </div>

            {bills.length > 0 && (
              <div className="mb-6">
                <h3 className="font-semibold text-gray-900 mb-2">Your Bills:</h3>
                <div className="space-y-2">
                  {bills.map((bill, index) => (
                    <div key={index} className="p-3 bg-gray-50 rounded-lg">
                      <div className="font-medium">{bill.name}</div>
                      <div className="text-sm text-gray-600">
                        ${bill.amount} - {bill.frequency} - Due {bill.startDate}
                      </div>
                    </div>
                  ))}
                </div>
              </div>
            )}

            <div className="flex justify-between">
              <button
                onClick={() => setStep(1)}
                className="py-2 px-6 border border-gray-300 text-gray-700 rounded-lg hover:bg-gray-50"
              >
                Back
              </button>
              <button
                onClick={() => setStep(3)}
                className="py-2 px-6 bg-blue-600 text-white rounded-lg hover:bg-blue-700"
              >
                Next: Review
              </button>
            </div>
          </div>
        )}

        {/* Step 3: Review */}
        {step === 3 && (
          <div className="bg-white rounded-lg shadow-sm p-8">
            <h2 className="text-2xl font-bold text-gray-900 mb-2">
              Review Your Setup
            </h2>
            <p className="text-gray-600 mb-6">
              Here's what you've added. You can manage these in your dashboard.
            </p>

            <div className="space-y-6 mb-8">
              <div>
                <h3 className="font-semibold text-gray-900 mb-3">Paydays ({paydays.length})</h3>
                <div className="space-y-2">
                  {paydays.map((payday, index) => (
                    <div key={index} className="p-3 bg-green-50 border border-green-200 rounded-lg">
                      <div className="font-medium text-green-900">${payday.amount}</div>
                      <div className="text-sm text-green-700">
                        {payday.frequency} - Starting {payday.startDate}
                      </div>
                    </div>
                  ))}
                </div>
              </div>

              <div>
                <h3 className="font-semibold text-gray-900 mb-3">Bills ({bills.length})</h3>
                <div className="space-y-2">
                  {bills.map((bill, index) => (
                    <div key={index} className="p-3 bg-red-50 border border-red-200 rounded-lg">
                      <div className="font-medium text-red-900">{bill.name}</div>
                      <div className="text-sm text-red-700">
                        ${bill.amount} - {bill.frequency} - Due {bill.startDate}
                      </div>
                    </div>
                  ))}
                </div>
              </div>
            </div>

            <div className="flex justify-between">
              <button
                onClick={() => setStep(2)}
                className="py-2 px-6 border border-gray-300 text-gray-700 rounded-lg hover:bg-gray-50"
              >
                Back
              </button>
              <button
                onClick={finishOnboarding}
                className="py-2 px-6 bg-green-600 text-white rounded-lg hover:bg-green-700"
              >
                Go to Dashboard
              </button>
            </div>
          </div>
        )}
      </main>
    </div>
  );
}
