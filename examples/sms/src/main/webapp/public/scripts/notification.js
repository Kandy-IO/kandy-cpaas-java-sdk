(function (window) {
  function syntaxHighlight (obj) {
    let json = JSON.stringify(obj, undefined, 4)
    json = json.replace(/&/g, '&amp;').replace(/</g, '&lt;').replace(/>/g, '&gt;')

    return json.replace(/("(\\u[a-zA-Z0-9]{4}|\\[^u]|[^\\"])*"(\s*:)?|\b(true|false|null)\b|-?\d+(?:\.\d*)?(?:[eE][+-]?\d+)?)/g, function (match) {
      var cls = 'number'
      if (/^"/.test(match)) {
        if (/:$/.test(match)) {
          cls = 'key'
        } else {
          cls = 'string'
        }
      } else if (/true|false/.test(match)) {
        cls = 'boolean'
      } else if (/null/.test(match)) {
        cls = 'null'
      }
      return '<span class="' + cls + '">' + match + '</span>'
    })
  }

  function render () {
    const { host, protocol } = window.location

    window.fetch(`${protocol}//${host}/notifications`)
      .then(res => res.json().then(data => ({ status: res.status, body: data })))
      .then((res) => {
        if (res.status === 200 && res.body.length) {
          const notifications = res.body
          const notificationWrapper = document.getElementById('notification')
          let html = ''

          for (let i in notifications) {
            html += '<pre class="notification">' + syntaxHighlight(notifications[i]) + '</pre>'
          }

          notificationWrapper.innerHTML = html
        }
      })
  }

  window.smsNotification = {
    render
  }
})(window)
